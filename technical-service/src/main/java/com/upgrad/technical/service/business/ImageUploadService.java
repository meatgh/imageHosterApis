package com.upgrad.technical.service.business;

import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageUploadService {

    @Autowired
    private ImageDao imageDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity upload(ImageEntity imageEntity, final String authorizationToken) throws UploadFailedException {
        //call createImage() method for imageDao and pass imageEntity as an argument
        //Note that createImage() method returns the created image of type ImageEntity
        //Also note that upload() method returns the value returned by createImage() method
        //Write code here//

        //Firstly check that the access token is a valid one(exists in the user_auth_tokens table)
        //Call getUserAuth() method for imageDao and pass authorizationToken as an argument
        //Receive the value returned by getUserAuthToken in UserAuthTokenEntity type object(name it as userAuthTokenEntity)
        //Write code here//
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorizationToken);
        if (userAuthTokenEntity == null) {
            //throw user defined exception(UploadFailedException has been implemented), so throw UploadFailedException
            //Exception constructor takes two arguments
            //Pass first argument as Exception code(String)(e.g. UP-001 )
            //Pass second argumment as Exception message(String) (e.g. User is not Signed in, sign in to upload an image)
            //Write code here//
            throw new UploadFailedException("UP-001", "User is not Signed in, sign in to upload an image");
        }

        //Control reaches here if the access token is valid
        //Before persisting the object to the database we need to set the user_id of imageEntity
        imageEntity.setUser_id(userAuthTokenEntity.getUser());
        //Call the createImage() method for imageDao and pass imageEntity as an argument
        //Note that createImage() method returns ImageEntity type object
        //Receive the value returned by createImage() method in ImageEntity type object
        //Return that object
        //Write code here//
        return imageDao.createImage(imageEntity);


    }
}
