$(window).on("load", function() {
    "use strict";

    

    //  ============= POST PROJECT POPUP FUNCTION =========

    $(".post_project").on("click", function(){
        $(".post-popup.pst-pj").addClass("active");
        document.getElementById("esPrivado").checked = false;
        document.getElementById("publico-privado").innerHTML = "Público";
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".post-project > a").on("click", function(){
        $(".post-popup.pst-pj").removeClass("active");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= POST ALBUM POPUP FUNCTION =========

    $(".post_album").on("click", function(){
        $(".post-popup.pst-album").addClass("active");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".post-album > a").on("click", function(){
        $(".post-popup.pst-album").removeClass("active");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= POST FOTO POPUP FUNCTION =========

    $(".post_foto").on("click", function(){
        $(".post-popup.pst-foto").addClass("active");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".post-foto > a").on("click", function(){
        $(".post-popup.pst-foto").removeClass("active");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= POST JOB POPUP FUNCTION =========

    $(".post-jb").on("click", function(){
        $(".post-popup.job_post").addClass("active");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".post-project > a").on("click", function(){
        $(".post-popup.job_post").removeClass("active");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= SIGNIN CONTROL FUNCTION =========

    $('.sign-control li').on("click", function(){
        var tab_id = $(this).attr('data-tab');
        $('.sign-control li').removeClass('current');
        $('.sign_in_sec').removeClass('current');
        $(this).addClass('current animated fadeIn');
        $("#"+tab_id).addClass('current animated fadeIn');
        return false;
    });

    //  ============= SIGNIN TAB FUNCTIONALITY =========

    $('.signup-tab ul li').on("click", function(){
        var tab_id = $(this).attr('data-tab');
        $('.signup-tab ul li').removeClass('current');
        $('.dff-tab').removeClass('current');
        $(this).addClass('current animated fadeIn');
        $("#"+tab_id).addClass('current animated fadeIn');
        return false;
    });

    //  ============= SIGNIN SWITCH TAB FUNCTIONALITY =========

    $('.tab-feed ul li').on("click", function(){
        var tab_id = $(this).attr('data-tab');
        $('.tab-feed ul li').removeClass('active');
        $('.product-feed-tab').removeClass('current');
        $(this).addClass('active animated fadeIn');
        $("#"+tab_id).addClass('current animated fadeIn');
        return false;
    });

    //  ============= COVER GAP FUNCTION =========

    var gap = $(".container").offset().left;
    $(".cover-sec > a, .chatbox-list").css({
        "right": gap
    });

    //  ============= EDITAR POST FUNCTION =========

    $("[class^='editar-post-open-']").on("click", function(){
        var id =  $(this).attr('class').replace('editar-post-open-','');
        $("#editar-post-box-"+ id).addClass("open");
        $(".wrapper").addClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    $(".close-box").on("click", function(){
        $("[id^=editar-post-box]").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    $(".close-editar-post-box").on("click", function(){
        $("[id^=editar-post-box]").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    //  ============= ELIMINAR POST FUNCTION =========

    $("[class^='eliminar-post-open-']").on("click", function(){
        var id =  $(this).attr('class').replace('eliminar-post-open-','');
        $("#eliminar-post-box-"+id).addClass("open");
        $(".wrapper").addClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });
    $(".close-box").on("click", function(){
        $("[id^=eliminar-post-box]").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    $(".close-eliminar-post-box").on("click", function(){
        $("[id^=eliminar-post-box]").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });


    //  ============= EDITAR PRIVACIDAD POST FUNCTION =========

    $("[class^='cambiar-privacidad-post-open-']").on("click", function(){
        var id =  $(this).attr('class').replace('cambiar-privacidad-post-open-','');
        $("#cambiar-privacidad-post-box-"+id).addClass("open");
        $(".wrapper").addClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });
    $(".close-box").on("click", function(){
        $("[id^=cambiar-privacidad-post-box]").removeClass("open");
        $(".wrapper").removeClass("overlay");
        //$("[class^='esPrivado-edit-']").checked = $("[class^='esPrivado-edit-']").defaultValue;
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    $(".close-cambiar-privacidad-post-box").on("click", function(){
        var defaultCheked = $("[class^='esPrivado-edit-']").defaultChecked;
        $("[id^=cambiar-privacidad-post-box]").removeClass("open");
        $(".wrapper").removeClass("overlay");
        //$("[class^='esPrivado-edit-']").checked = $("[class^='esPrivado-edit-']").defaultValue;
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    //  ============= FECHA NACIMIENTO EDIT FUNCTION =========

    $(".fecha-nacimiento-open").on("click", function(){
        $("#editar-fecha-nacimiento-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#editar-fecha-nacimiento-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    $(".close-fecha-nacimiento-box").on("click", function(){
        $("#editar-fecha-nacimiento-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    //  ============= CAMBIAR FOTO PERFIL FUNCTION =========

    $(".cambiar-foto-perfil-box-open").on("click", function(){
        $("#cambiar-foto-perfil-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#cambiar-foto-perfil-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    $(".close-cambiar-foto-perfil-box").on("click", function(){
        $("#cambiar-foto-perfil-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

/*
    //  ============= ETIQUETAR FUNCTION =========

    $(".etiquetar-amigos-box-open").on("click", function(){
        $("#etiquetar-amigos-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#etiquetar-amigos-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    $(".close-etiquetar-amigos-box").on("click", function(){
        $("#etiquetar-amigos-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    $(".close-etiquetar-amigos-box").on("click", function(){
        $("#etiquetar-amigos-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });
    $("#guardar-etiquetas-btn").on("click", function(){
        $("#etiquetados").text = $("#amigos-cbBox").options[$("#amigos-cbBox").selectedIndex].text;
        $("#etiquetar-amigos-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
        //var cbBoxAmigos = $("#amigos-cbBox");
        //var etiquetadosField = $("#etiquetados");
        //var idUsuario = cbBoxAmigos.options[cbBoxAmigos.selectedIndex].value;

        //etiquetadosField.text += cbBoxAmigos.options[cbBoxAmigos.selectedIndex].text +", ";
        //$("#amigos-cbBox option[value='" + idUsuario + "']").remove();
        //if(cbBoxAmigos.options.size = 0){
        //    $("#etiquetar-btn").disabled = true;
        //}
    });
*/

    //  ============= EXPERIENCE EDIT FUNCTION =========

    $(".exp-bx-open").on("click", function(){
        $("#experience-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#experience-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= EDUCATION EDIT FUNCTION =========

    $(".ed-box-open").on("click", function(){
        $("#education-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#education-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= LOCATION EDIT FUNCTION =========

    $(".lct-box-open").on("click", function(){
        $("#location-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#location-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    $(".close-location-box").on("click", function(){
        $("#location-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    //  ============= LUGAR DE ESTUDIO EDIT FUNCTION =========

    $(".centro-estudio-open").on("click", function(){
        $("#centro-estudio-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#centro-estudio-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    $(".close-centro-estudio-box").on("click", function(){
        $("#centro-estudio-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });

    //  ============= CENTRO DE TRABAJO EDIT FUNCTION =========

    $(".centro-trabajo-open").on("click", function(){
        $("#centro-trabajo-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#centro-trabajo-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    $(".close-centro-trabajo-box").on("click", function(){
        $("#centro-trabajo-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        $(".ed-opts-open").next(".ed-options").removeClass("active");
        return false;
    });



    //  ============= SKILLS EDIT FUNCTION =========

    $(".skills-open").on("click", function(){
        $("#skills-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#skills-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= ESTABLISH EDIT FUNCTION =========

    $(".esp-bx-open").on("click", function(){
        $("#establish-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#establish-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });

    //  ============= EMPLOYEE EDIT FUNCTION =========

    $(".emp-open").on("click", function(){
        $("#total-employes").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#total-employes").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });



    //  =============== Ask a Question Popup ============

    $(".ask-question").on("click", function(){
        $("#question-box").addClass("open");
        $(".wrapper").addClass("overlay");
        return false;
    });
    $(".close-box").on("click", function(){
        $("#question-box").removeClass("open");
        $(".wrapper").removeClass("overlay");
        return false;
    });


    //  ============== ChatBox ============== 


    $(".chat-mg").on("click", function(){
        $(this).next(".conversation-box").toggleClass("active");
        return false;
    });
    $(".close-chat").on("click", function(){
        $(".conversation-box").removeClass("active");
        return false;
    });

    //  ================== Edit Options Function =================


    $(".ed-opts-open").on("click", function(){
        $(this).next(".ed-options").toggleClass("active");
        return false;
    });


    // ============== Menu Script =============

    $(".menu-btn > a").on("click", function(){
        $("nav").toggleClass("active");
        return false;
    });


    //  ============ Notifications Open =============

    $(".not-box-open").on("click", function(){
        $(this).next(".notification-box").toggleClass("active");
    });

    // ============= User Account Setting Open ===========

    $(".user-info i").on("click", function(){
        $(".user-info").next(".user-account-settingss").toggleClass("active");
    });

    //  ============= FORUM LINKS MOBILE MENU FUNCTION =========

    $(".forum-links-btn > a").on("click", function(){
        $(".forum-links").toggleClass("active");
        return false;
    });
    $("html").on("click", function(){
        $(".forum-links").removeClass("active");
    });
    $(".forum-links-btn > a, .forum-links").on("click", function(){
        e.stopPropagation();
    });

    //  ============= PORTFOLIO SLIDER FUNCTION =========

    $('.profiles-slider').slick({
        slidesToShow: 3,
        slck:true,
        slidesToScroll: 1,
        prevArrow:'<span class="slick-previous"></span>',
        nextArrow:'<span class="slick-nexti"></span>',
        autoplay: true,
        dots: false,
        autoplaySpeed: 2000,
        responsive: [
        {
          breakpoint: 1200,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 2
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
        // You can unslick at a given breakpoint now by adding:
        // settings: "unslick"
        // instead of a settings object
      ]


    });





});


