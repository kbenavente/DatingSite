<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html lang="en">
	<head>
		
		<meta charset="UTF-8">
		<title>Love Bird - Profile</title>
		
		<link rel="stylesheet" href="stylsheets/bootstrap.min.css" />
		<link rel="stylesheet" href="stylsheets/style.css" />
		
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<script src="./javascript/script.js"></script>
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	
	
	</head>
	
	<body>
	
		<div class="container-fluid p-0 bg-primary">
			
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
						<li class="nav-item">
							<a class="nav-link" href="LoveBirdLogout"><button class="btn btn-outline-info">Logout</button></a>
						</li>
					
					</ul>
				
				</div>
			
			</nav>
			
			<!-- Header and Navigation Bar End -->
			
			<!-- Content Start -->
			
			<div class="container-fluid pt-5">
				
				<div class="container-fluid m-5 p-5 mx-auto w-100 bg-white rounded">
				
					<div class="row profile-container border-bottom">
						
						<!-- Profile image and name -->
						
						<div class="col-lg-6">
						
							<div class="img-container mx-auto">
								<img src="ProfileImage" alt="Profile-Image" />
							</div>
							<h2 class="text-center">${firstName} ${lastName}</h2>
						
						</div>
						
						<!-- Bio and information -->
						
						<div class="col-lg-6 align-items-center mt-5">
						
							<p class="w-100 p-3">${bio}</p>
							<p class="font-weight-bold p-3">${city}, ${state}</p>
							<p class="font-weight-bold p-3">${age} years old</p>
							
							<c:if test="${otherUserID.equals('')}">
							
								<div class="container-fluid text-center">
								
									<a href="LoveBirdEditProfile"><button id="editProfile" class="btn btn-outline-secondary m-4">Edit Profile</button></a>
									
									<a href="LoveBirdUploadImage"><button class="btn btn-outline-secondary m-4">Add an Image</button></a>
									
									<a href="LoveBirdChangePassword"><button class="btn btn-outline-secondary m-4">Change Password</button></a>
								
								</div>
							
							</c:if>
						
						</div>
						
					</div>
					
					<div class="row profile-nav w-100 p-0 m-0">
						
						<c:choose>
						
							<c:when test="${otherUserID.equals('')}">
							
								<div class="col-4 p-0 w-100">
							
									<button class="current" id="profileImages"><img width="24px" height="24px"src="./icons/images.svg" alt="Images Icon" /></button>
							
								</div>
						
								<div class="col-4 p-0 w-100">
							
									<button class="" id="profileLiked"><img width="24px" height="24px"src="./icons/heart.svg" alt="Heart Icon" /></button>
						
								</div>
						
								<div class="col-4 p-0 w-100">
							
									<button class="" id="profileMatched"><img width="24px" height="24px"src="./icons/fire-alt.svg" alt="Fire Icon" /></button>
						
								</div>
							
							</c:when>
							
							<c:otherwise>
							
								<div class="col-12 p-0 w-100">
							
									<button class="current" id="profileImages"><img width="24px" height="24px"src="./icons/images.svg" alt="Images Icon" /></button>
							
								</div>
							
							</c:otherwise>
						
						</c:choose>
						
						
						
					</div>
					
					<div id="profileImagesContainer" class="container-fluid pt-3">
						
						<div class="row">
						
							<c:forEach items="${imageIDs}" var="imageID" varStatus="status">
							
								<c:if test="${imageIDs.size() == 4}">
								
									<div class="w-100"></div>
								
								</c:if>
								
								<div class="col-xl-4 profile-images-container p-2">
								
									<c:if test="${otherUserID.isEmpty()}">
								
										<a href="LoveBirdDeleteImage?${imageID}"><button class="delete position-absolute">X</button></a>
								
									</c:if>
									
									<img class="rounded" src="LoveBirdUserImage?${imageID}&otherUserID=${otherUserID}" alt="User Image" />
								
								</div>
							
							</c:forEach>
							
						</div>
						
					</div>
					
					<div id="profileLikedContainer" class="container-fluid pt-3" style="display:  none;">
					
						<div class="row w-100">
						
							<div class="col-lg-4 mx-aut">
								
								<div class="w-100 mx-auto bg-light p-4 mt-2 rounded text-center">
								
									<div class="liked-wrapper-image mx-auto">
										
										<img src="https://d3vlh9j4b368l5.cloudfront.net/production/people/32/296fa380fb5d73bf620ae8c22bb3c8408382aa87-square.jpg?1426357302" alt="Something" />
										
									</div>
									<p class="text-center">Sally Smith</p>
									<button class="btn btn-outline-danger m-2 p-2">Unlike</button>
									<button class="btn btn-outline-primary m-2 p-2">View Profile</button>
									
								
								</div>
								
							</div>
							
							<div class="col-lg-4 mx-aut">
								
								<div class="w-100 mx-auto bg-light p-4 mt-2 rounded text-center">
									
									<div class="liked-wrapper-image mx-auto">
										
										<img src="https://previews.123rf.com/images/ammentorp/ammentorp1704/ammentorp170400278/77014947-square-portrait-of-young-woman-with-sunglasses-against-beige-background-caucasian-female-fashion-mod.jpg" alt="something" />
										
									</div>
									<p class="text-center">Jamie Something</p>
									<button class="btn btn-outline-danger m-2 p-2">Unlike</button>
									<button class="btn btn-outline-primary m-2 p-2">View Profile</button>
								
								</div>
							
							</div>
							
							<div class="col-lg-4 mx-aut">
								
								<div class="w-100 mx-auto bg-light p-4 mt-2 rounded text-center">
									
									<div class="liked-wrapper-image mx-auto">
										
										<img src="https://previews.123rf.com/images/chesterf/chesterf1207/chesterf120700088/14525771-beautiful-blonde-woman-closeup-face-portrait-square-frame.jpg" alt="something" />
										
									</div>
									<p class="text-center">Jane Doe</p>
									<button class="btn btn-outline-danger m-2 p-2">Unlike</button>
									<button class="btn btn-outline-primary m-2 p-2">View Profile</button>
								
								</div>
							
							</div>
							
						</div>
					
					</div>
					
					<div id="profileMatchedContainer" class="container-fluid pt-3" style="display: none;">
						
						<div class="row w-100">
							
							<div class="col-lg-4 mx-aut">
								
								<div class="w-100 mx-auto bg-light p-4 mt-2 rounded text-center">
									
									<div class="matched-wrapper-image mx-auto">
										
										<img src="https://d3vlh9j4b368l5.cloudfront.net/production/people/32/296fa380fb5d73bf620ae8c22bb3c8408382aa87-square.jpg?1426357302" alt="Something" />
									
									</div>
									<p class="text-center">Sally Smith</p>
									<button class="btn btn-outline-danger m-2 p-2">Unlike</button>
									<button class="btn btn-outline-primary m-2 p-2">View Profile</button>
									<button class="btn btn-outline-secondary m-2 p-2">Message</button>
								
								
								</div>
							
							</div>
							
							<div class="col-lg-4 mx-aut">
								
								<div class="w-100 mx-auto bg-light p-4 mt-2 rounded text-center">
									
									<div class="matched-wrapper-image mx-auto">
										
										<img src="https://previews.123rf.com/images/ammentorp/ammentorp1704/ammentorp170400278/77014947-square-portrait-of-young-woman-with-sunglasses-against-beige-background-caucasian-female-fashion-mod.jpg" alt="something" />
									
									</div>
									<p class="text-center">Jamie Something</p>
									<button class="btn btn-outline-danger m-2 p-2">Unlike</button>
									<button class="btn btn-outline-primary m-2 p-2">View Profile</button>
									<button class="btn btn-outline-secondary m-2 p-2">Message</button>
								
								</div>
							
							</div>
						
						</div>
					
					</div>
					
				
				</div>
				
			</div>
			
			<!-- Content End -->
			<!-- Footer Start -->
			
			<div class="container-fluid bg-primary border-top">
				
				<div class="row p-5">
					
					<div class="mx-auto text-center">
						
						<a class="navbar-brand" href="#"><h2 class="text-white">love bird</h2></a>
						
						<br/>
						
						<hr/>
						
						<a class="text-white-50 p-2" href="index.html">Home</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="PrivacyStatement.html">Privacy</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2"  href="TermsAndConditions.html">Terms &amp; Conditions</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="MyAccount.html">Account</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="">Payment</a><span class="text-white-50">|</span>
						<a class="text-white-50 p-2" href="ForgotPassword.html">Forgot Password</a>
					
					</div>
				
				</div>
			
			</div>
			
			<!-- Footer End -->
		
		</div>
	
	</body>

</html>