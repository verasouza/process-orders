package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.entities.Order;
import com.vsouza.processorders.dto.entities.OrderProduct;
import com.vsouza.processorders.dto.entities.Product;
import com.vsouza.processorders.dto.entities.User;
import com.vsouza.processorders.dto.model.UserOrder;
import com.vsouza.processorders.repositories.OrderRepository;
import com.vsouza.processorders.repositories.ProductRepository;
import com.vsouza.processorders.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    final UserRepository userRepository;
    final OrderRepository orderRepository;
    final ProductRepository productRepository;

    // Regex que lida com espaços irregulares entre os campos
    private static final Pattern LINE_PATTERN = Pattern.compile(
            "(?<userId>\\d{10})" +
                    "\\s+" +
                    "(?<userName>\\S[\\S ]{0,43}\\S)" +
                    "\\s*" +
                    "(?<orderId>\\d{10})" +
                    "(?<productId>\\d{10})" +
                    "\\s*" +
                    "(?<value>\\d+\\.\\d+)" +
                    "(?<date>\\d{8})"
    );



    //ler o arquivo na pasta data
    public void readOrdersFromFile(String filePath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = LINE_PATTERN.matcher(line.trim());
                if (matcher.matches()) {
                    try {
                        // Extrair dados da linha
                        Long userId = Long.parseLong(matcher.group(1).trim());
                        String userName = matcher.group(2).trim();
                        Long orderId = Long.valueOf(matcher.group(3).trim());
                        Long productId = Long.valueOf(matcher.group(4).trim());
                        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(matcher.group(5).trim()));
                        String date = matcher.group(6).trim();
                        LocalDate dateFinal = LocalDate.parse(date, formatter);

                        // 1. Primeiro, garantir que o produto existe
                        Product product = productRepository.findById(productId)
                                .orElseGet(() -> {
                                    Product newProduct = new Product();
                                    newProduct.setId(productId);
                                    newProduct.setProductPrice(value);
                                    newProduct.setOrderProducts(new ArrayList<>());
                                    return productRepository.save(newProduct);
                                });

                        // 2. Depois, garantir que o usuário existe
                        User user = userRepository.findById(userId)
                                .orElseGet(() -> {
                                    User newUser = User.builder()
                                            .id(userId)
                                            .name(userName)
                                            .orders(new ArrayList<>())
                                            .build();
                                    return userRepository.save(newUser);
                                });

                        // 3. Criar ou recuperar o pedido
                        Order order = orderRepository.findById(orderId)
                                .orElseGet(() -> {
                                    Order newOrder = Order.builder()
                                            .id(orderId)
                                            .date(dateFinal)
                                            .orderProducts(new ArrayList<>())
                                            .user(user)
                                            .build();
                                    return orderRepository.save(newOrder);
                                });

                        // 4. Criar e salvar OrderProduct apenas se não existir
                        boolean orderProductExists = order.getOrderProducts().stream()
                                .anyMatch(op -> op.getProduct().getId().equals(productId));

                        if (!orderProductExists) {
                            OrderProduct orderProduct = new OrderProduct(order, product);

                            order.getOrderProducts().add(orderProduct);
                            product.getOrderProducts().add(orderProduct);

                            orderRepository.save(order);
                        }

                    } catch (Exception e) {
                        log.error("Erro ao processar linha: " + line, e);
                    }
                } else {
                    log.info("Linha não corresponde ao padrão esperado: " + line);
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler arquivo: " + e.getMessage(), e);
        }
    }
}
