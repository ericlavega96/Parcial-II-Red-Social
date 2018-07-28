
<!DOCTYPE html>
<html>

<!-- Mirrored from gamboldesigns.net/Demo/HTML/sign-in.ftl by HTTrack Website Copier/3.x [XR&CO'2014], Thu, 19 Jul 2018 03:36:44 GMT -->
<head>
	<meta charset="UTF-8">
	<title>WorkWise Html Template</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<link rel="stylesheet" type="text/css" href="css/animate.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/line-awesome.css">
	<link rel="stylesheet" type="text/css" href="css/line-awesome-font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="lib/slick/slick.css">
	<link rel="stylesheet" type="text/css" href="lib/slick/slick-theme.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/responsive.css">
	<script>
        var password = document.getElementById("registrarUsuarioPassword");
        var repetirPassword = document.getElementById("repetirPassword");

        repetirPassword.addEventListener("input", function (event) {
            if (password.value != repetirPassword.value) {
                email.setCustomValidity("Debe de repetir la contraseña correctamente.");
            }
        });
	</script>
</head>

<body class="sign-in">
	<div class="wrapper">
		<div class="sign-in-page">
			<div class="signin-popup">
				<div class="signin-pop">
					<div class="row">
							<div class="login-sec">
								<ul class="sign-control">
									<li data-tab="tab-1" class="current"><a href="#" title="">Iniciar Sesión</a></li>
									<li data-tab="tab-2"><a href="#" title="">Registrarte</a></li>
								</ul>
								<div class="sign_in_sec current" id="tab-1">
									<h3>Iniciar Sesión</h3>
									<form action="/procesarUsuario" method="post">
										<div class="row">
											<div class="col-lg-12 no-pdd">
												<div class="sn-field">
													<input type="text" name="email" placeholder="Correo electrónico">
													<i class="la la-user"></i>
												</div><!--sn-field end-->
											</div>
											<div class="col-lg-12 no-pdd">
												<div class="sn-field">
													<input type="password" name="password" placeholder="Contraseña">
													<i class="la la-lock"></i>
												</div>
											</div>
											<div class="col-lg-12 no-pdd">
												<div class="checky-sec">
													<div class="fgt-sec">
														<input type="checkbox" name="recordar" id="c1">
														<label for="c1">
															<span></span>
														</label>
														<small>Recordarme</small>
													</div><!--fgt-sec end-->
												</div>
											</div>
											<div class="col-lg-12 no-pdd">
												<button type="submit">Iniciar Sesión</button>
											</div>
										</div>
									</form>
								</div><!--sign_in_sec end-->
								<div class="sign_in_sec" id="tab-2">
									<h3>Registrarte</h3>
									<div class="dff-tab current" id="tab-3">
										<form action="/registrarUsuario" method="post">
											<div class="row">
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="text" name="nombres" placeholder="Nombres" required>
														<i class="la la-user"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="text" name="apellidos" placeholder="Apellidos" required>
														<i class="la la-user"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
														<div id="gender">
															<small>Sexo</small><br><br>
															<input type="checkbox" name="cbMasculino">
															<label for="c1">
																<span></span>
															</label>
															<small>Masculino</small>
															<input type="checkbox" name="cbFemenino">
															<label>
																<span></span>
															</label>
															<small>Femenino</small>
														</div>
												</div>
												<div class="col-lg-12 no-pdd" id="bday">
													<small>Fecha de Nacimiento</small><br><br>
													<div class="sn-field">
														<input type="date" name="fechaNacimiento" required>
														<i class="la la-birthday-cake"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<i class="la la-globe"></i>
														<select name="cbBoxPais" required>
															<#list paises as pais>
																<option>${pais.pais}</option>
															</#list>
														</select>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<i class="la la-building"></i>
														<select name="cbBoxCiudad" required>
															<#list ciudades as ciudad>
																<option>${ciudad.ciudad}</option>
															</#list>
														</select>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="text" name="lugarEstudio" placeholder="Lugar de Estudios">
														<i class="la la-university"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="text" name="empleo" placeholder="Empleo">
														<i class="la la-user-times"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="text" name="email" placeholder="Correo electrónico">
														<i class="la la-user"></i>
													</div><!--sn-field end-->
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="password"  id="registrarUsuarioPassword" name="password" placeholder="Contraseña" required>
														<i class="la la-lock"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<div class="sn-field">
														<input type="password" name="repetirPassword" placeholder="Repetir Contraseña" required>
														<i class="la la-lock"></i>
													</div>
												</div>
												<div class="col-lg-12 no-pdd">
													<button type="submit" value="submit">Registrar</button>
												</div>
											</div>
										</form>
									</div><!--dff-tab end-->
								</div>		
							</div><!--login-sec end-->
					</div>		
				</div><!--signin-pop end-->
			</div><!--signin-popup end-->
			<div class="footy-sec">
				<div class="container">
					<p><img src="images/copy-icon.png" alt="">Copyright 2018</p>
				</div>
			</div><!--footy-sec end-->
		</div><!--sign-in-page end-->


	</div><!--theme-layout end-->

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/popper.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/slick/slick.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
	<script>
        $(document).ready(function(){
            $('input:checkbox').click(function() {
                $('input:checkbox').not(this).prop('checked', false);
            });
        });
	</script>
</body>

<!-- Mirrored from gamboldesigns.net/Demo/HTML/sign-in.ftl by HTTrack Website Copier/3.x [XR&CO'2014], Thu, 19 Jul 2018 03:36:46 GMT -->
</html>