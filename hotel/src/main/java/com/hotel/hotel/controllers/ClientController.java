package com.hotel.hotel.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.hotel.models.Client;
import com.hotel.hotel.models.Order;
import com.hotel.hotel.services.ClientService;
import com.hotel.hotel.services.OrderService;

@RestController // класс является контроллевром
@RequestMapping("/clients") // по этому адресу будут выполняться запросы
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService; 

    // добавление заказа
    @PostMapping("/{id}/orders/")
    public void addOrder(@PathVariable Integer id, @RequestBody Order order){
        Client client = clientService.getClientById(id);
        order.setClient(client);
        orderService.save(order);
    }

    // получение списка всех клиентов
    @GetMapping("")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    // получение клиента по id
    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable Integer id){
        try{
            Client client = clientService.getClientById(id);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
    }

    // получение списка клиентов по имени
    @GetMapping("/name/{name}")
    public List<Client> getByName(@PathVariable String name){
        return clientService.getClientByFirstName(name);
    }

    // сохранение клиента
    @PostMapping("/")
    public void add(@RequestBody Client client){
        clientService.saveClient(client);
    }

    // удаление клиента по id
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        clientService.deleteClient(id);
    }

    // редактирование клиента
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Client client, @PathVariable Integer id){
        try{
            Client baseClient = clientService.getClientById(id);
            baseClient.updateClient(client);
            clientService.saveClient(baseClient);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch(NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
