package com.example.userservice.Service;

import com.example.userservice.DTO.RegisterRequest;
import com.example.userservice.DTO.UserDTO;
import com.example.userservice.entity.User;
import com.example.userservice.entity.UserRole;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class UserService {
@Autowired
    DataSource dataSource;
    public UserDTO register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        // In PostgreSQL, to return all columns after an insert, we use RETURNING *
        String sql = "INSERT INTO users (email, password, first_name, last_name) VALUES (?, ?, ?, ?) RETURNING id, create_at, updated_at";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());

            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setCreateAt(user.getCreateAt());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setId(user.getId());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;

    }

    public  UserDTO getUserProfile(Long userId) {
        UserDTO userDTO = new UserDTO();
        String sql = "SELECT * FROM USERS Where id = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            // now use a while loop to iterate over the result set and make the DTO
            while(rs.next()){
                userDTO.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                userDTO.setEmail(rs.getString("email"));
                userDTO.setFirstName(rs.getString("first_name"));
                userDTO.setLastName(rs.getString("last_name"));
                userDTO.setId(rs.getLong("id"));
                userDTO.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    public boolean existsById(Long userId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
