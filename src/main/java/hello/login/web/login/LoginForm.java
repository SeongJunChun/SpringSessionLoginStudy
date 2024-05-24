package hello.login.web.login;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginForm {

	@NotEmpty
	private String loginId;

	@NotEmpty
	private String password;

}
