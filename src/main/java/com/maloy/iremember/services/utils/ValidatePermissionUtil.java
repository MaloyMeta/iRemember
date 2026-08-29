package com.maloy.iremember.services.utils;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.user.User;
import com.maloy.iremember.enums.user.UserRole;
import com.maloy.iremember.exceptions.user.PermissionDeniedException;
import org.springframework.stereotype.Component;

@Component
public class ValidatePermissionUtil {
    public static void validatePermission(User currentUser, Client client){
        boolean isOwner = client.getLinkedManager().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        if(!isOwner && !isAdmin){
            throw new PermissionDeniedException("Client not belongs to you");
        }
    }
}
