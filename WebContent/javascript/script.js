jQuery(function() {

    // Profile Page Event Handlers

    $("#profileImages").on("click", function() {

        let profileButtonLike = $("#profileLiked");
        let profileButtonImages = $("#profileImages");
        let profileButtonMatched = $("#profileMatched");

        profileButtonImages.addClass("current");
        $("#profileImagesContainer").show();
        profileButtonLike.removeClass("current");
        $("#profileLikedContainer").hide();
        profileButtonMatched.removeClass("current");
        $("#profileMatchedContainer").hide();

    });

    $("#profileLiked").on("click", function() {

        let profileButtonLike = $("#profileLiked");
        let profileButtonImages = $("#profileImages");
        let profileButtonMatched = $("#profileMatched");

        profileButtonLike.addClass("current");
        $("#profileLikedContainer").show();
        profileButtonImages.removeClass("current");
        $("#profileImagesContainer").hide();
        profileButtonMatched.removeClass("current");
        $("#profileMatchedContainer").hide();

    });

    $("#profileMatched").on("click", function() {

        let profileButtonLike = $("#profileLiked");
        let profileButtonImages = $("#profileImages");
        let profileButtonMatched = $("#profileMatched");

        profileButtonMatched.addClass("current");
        $("#profileMatchedContainer").show();
        profileButtonLike.removeClass("current");
        $("#profileLikedContainer").hide();
        profileButtonImages.removeClass("current");
        $("#profileImagesContainer").hide();

    });

});