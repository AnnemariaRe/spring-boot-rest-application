package org.annemariare.cats.rabbitmq;

import org.annemariare.cats.dto.CatDto;
import org.annemariare.cats.dto.OwnerDto;
import org.annemariare.cats.messaging.IdResponse;
import org.annemariare.cats.service.OwnerService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableRabbit
public class Receiver {
    @Autowired
    private OwnerService service;

    @RabbitListener(queues = "owner_save")
    public void receive(@Payload OwnerDto owner) {
        service.add(owner);
    }

    @RabbitListener(queues = "owner_all")
    public List<OwnerDto> receive() {
        return service.getAll();
    }

    @RabbitListener(queues = "owner_id")
    public OwnerDto receive_id(@Payload Long id) {
        return service.getOne(id);
    }

    @RabbitListener(queues = "owner_name")
    public OwnerDto receive(@Payload String name) {
        return service.getSomeByName(name);
    }

    @RabbitListener(queues = "owner_cats")
    public List<CatDto> receive(@Payload IdResponse data) {
        return service.getAllCats(data.getId(), data.getUsername());
    }

    @RabbitListener(queues = "owner_delete")
    public void receive(Long id) {
        service.delete(id);
    }

}
