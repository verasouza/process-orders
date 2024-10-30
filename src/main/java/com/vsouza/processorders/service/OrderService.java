package com.vsouza.processorders.service;

import com.vsouza.processorders.dto.entities.Order;
import com.vsouza.processorders.dto.entities.Product;
import com.vsouza.processorders.dto.entities.User;
import com.vsouza.processorders.dto.model.UserOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class OrderService {

    // Regex que lida com espaços irregulares entre os campos
    private static final Pattern LINE_PATTERN = Pattern.compile(
            "(\\d{10})" +              // userId: exatamente 10 dígitos
                    "\\s+" +                   // um ou mais espaços
                    "([A-Za-z ]+)" +          // nome: letras e espaços
                    "(\\d{10})" +             // orderId: 10 dígitos
                    "(\\d{10})" +             // productId: 10 dígitos
                    "\\s+" +                   // um ou mais espaços
                    "(\\d+\\.\\d{2})" +       // value: número decimal
                    "(\\d{8})"                // date: 8 dígitos
    );

    //ler o arquivo na pasta data
    public List<UserOrder> readOrdersFromFile(String filePath) {
        List<UserOrder> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = LINE_PATTERN.matcher(line.trim());
                if(matcher.matches()){

                    String userId = matcher.group(1).trim();
                    String userName = matcher.group(2).trim();
                    String orderId = matcher.group(3).trim();
                    String prodId = matcher.group(4).trim();
                    String value = matcher.group(5).trim();
                    String date = matcher.group(6).trim();

                    LocalDate dateFinal = LocalDate.parse(date, formatter);

                    // Criando objeto e adicionando à lista
                    User user = User.builder()
                            .name(userName)
                            .userId(Long.parseLong(userId))
                            .build();
                    Product product = Product.builder()
                            .productId(Long.valueOf(prodId))
                            .value(Double.parseDouble(value))
                            .build();
                    Order order = Order.builder()
                            .orderId(Long.parseLong(orderId))
                            .date(dateFinal)
                            .build();
                    order.addProduct(product);



                    UserOrder userOrder = new UserOrder();
                    userOrder.setUser(user);
                    userOrder.addOrder(order);
                    orders.add(userOrder);
                }else{
                    log.info("NAO DEU MATCH");
                }



            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return orders;
    }
}
