package com.shopbee.cartservice.repository;

import com.shopbee.cartservice.entity.Cart;
import com.shopbee.cartservice.shared.page.PageRequest;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CartRepository implements PanacheRepository<Cart> {

    public List<Cart> findByUsername(String username, PageRequest pageRequest) {
        return find("username", username).page(pageRequest.getPage() - 1, pageRequest.getSize()).list();
    }

    public Cart findByUsernameAndProductSlug(String slug, String username) {
        return find("productSlug = ?1 and username = ?2", slug, username).firstResult();
    }

    public void deleteByUsername(String username) {
        delete("username", username);
    }

    public long getTotal(String username) {
        return count("username", username);
    }
}
