package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.PermissionType;
import com.secure.FileShareApp.entity.RequestAccess;

import java.util.List;

public interface RequestAccessService {

    RequestAccess shareFileWithUser(String ownerId,String receiverId,String fileId,PermissionType permissionType);

    RequestAccess approveAccessRequest(String ownerId,String requestId);

    RequestAccess rejectAccessRequest(String ownerId,String requestId);

    List<RequestAccess> getRequestsSentByUser(String userId);

    List<RequestAccess> getRequestsReceivedByUser(String ownerId);

    void revokeSharedAccess(String ownerId,String requestId,String fileId);

    void cancelAccessRequest(String requesterId,String requestId);

    List<RequestAccess> getPendingAccessRequests(String fileId);
}
