package web.mapper;

import model.User;
import org.mapstruct.Mapper;
import web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
