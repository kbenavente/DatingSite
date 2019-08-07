<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html lang="en">
	<head>
		
		<meta charset="UTF-8">
		<title>Love Bird - Edit Profile</title>
		
		<link rel="stylesheet" href="stylsheets/bootstrap.min.css" />
		<link rel="stylesheet" href="stylsheets/style.css" />
		
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	
	
	</head>

	<body>
	
		<div class="container-fluid p-0">
	
			<!-- Header and Navigation Bar Start -->
			
			<nav class="navbar navbar-expand-lg navbar-light position-fixed w-100">
				<a class="navbar-brand" href="index.html"><h1 class="text-primary">love bird</h1></a>
				<button class="navbar-toggler navbar-light" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				
				<div class="collapse navbar-collapse"  id="navbarColor01">
					<ul class="navbar-nav ml-auto align-items-center">
						<li class="nav-item">
							<a class="nav-link" href="index.html"><button class="btn btn-outline-primary">Home</button></a>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><button class="btn btn-outline-primary">Services</button></a>
							<div class="dropdown-menu mt-3" x-placement="bottom-start" style="position: absolute; will-change: transform; top: 0px; left: 0px; transform: translate3d(0px, 40px, 0px);">
								<a class="dropdown-item" href="#">Meet</a>
								<a class="dropdown-item" href="#">Chat</a>
							</div>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#"><button class="btn btn-outline-primary">About</button></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#"><button class="btn btn-outline-primary">Sign In</button></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#"><button class="btn btn-outline-primary">Sign Up</button></a>
						</li>
					
					</ul>
				
				</div>
			
			</nav>
			
			<!-- Header and Navigation Bar End -->
			
			<!-- Content Start -->
			
			<div class="container-fluid">
			
				<div class="row bg-warning vh-100 p-5">
					
					<form class="m-auto p-5 rounded" method="POST" action="LoveBirdEditProfile">
					
						<h2>Edit Profile</h2>
						
						<hr/>
						
						<div class="form-group">
							<textarea name="bio" class="form-control form-control-lg">${bio}</textarea>
						</div>
						
						<div class="form-group">
							<label class="col-form-label col-form-label-lg" for="firstName">First Name</label>
  							<input name="firstName" class="form-control form-control-lg" type="text" placeholder="First Name" id="firstName" value="${firstName}" />
  						</div>
  						
  						<div class="form-group">
  						  	<label class="col-form-label col-form-label-lg" for="lastName">First Name</label>
							<input name="lastName" class="form-control form-control-lg" type="text" placeholder="First Name" id="lastName" value="${lastName}" />
						</div>
						
						 <div class="form-group">
						 
					     	<label for="exampleSelect1">Gender Preference</label>
					      
					    	<select name="preference" class="form-control" id="exampleSelect1">
					      
					    	<option>Male</option>
					        <option>Female</option>
					        <option>Both</option>
					        
					      </select>
					      
					    </div>
					    
					    <h4 class="text-danger">${fildsCannotBeEmptyError}</h4>
						
						<button type="submit" class="btn btn-outline-warning mt-3">Edit Profile</button>
					
					</form>
					
				</div>
			
			</div>
			
			<!-- Content End -->
			<!-- Footer Start -->
			
			<div class="container-fluid bg-primary">
				
				<div class="row p-5">
					
					<div class="mx-auto text-center">
						
						<a class="navbar-brand" href="#"><h2 class="text-white">love bird</h2></a>
						
						<br/>
						
						<hr/>
						
						<a class="text-white-50 p-2" href="index.html">Home</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="PrivacyStatement.html">Privacy</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2"  href="TermsAndConditions.html">Terms &amp; Conditions</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="Profile.html">Account</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="Payment.html">Payment</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="ForgotPassword.html">Forgot Password</a>
					
					</div>
				
				</div>
			
			</div>
			
			<!-- Footer End -->
	
		</div>
	
	</body>

</html>