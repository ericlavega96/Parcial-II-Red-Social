$(function(){
    $.fn.atwho.debug = true
    var emojis = [
        "smile", "iphone", "girl", "smiley", "heart", "kiss", "copyright", "coffee",
        "a", "ab", "airplane", "alien", "ambulance", "angel", "anger", "angry",
        "arrow_forward", "arrow_left", "arrow_lower_left", "arrow_lower_right",
        "arrow_right", "arrow_up", "arrow_upper_left", "arrow_upper_right",
        "art", "astonished", "atm", "b", "baby", "baby_chick", "baby_symbol",
        "balloon", "bamboo", "bank", "barber", "baseball", "basketball", "bath",
        "bear", "beer", "beers", "beginner", "bell", "bento", "bike", "bikini",
        "bird", "birthday", "black_square", "blue_car", "blue_heart", "blush",
        "boar", "boat", "bomb", "book", "boot", "bouquet", "bow", "bowtie",
        "boy", "bread", "briefcase", "broken_heart", "bug", "bulb",
        "person_with_blond_hair", "phone", "pig", "pill", "pisces", "plus1",
        "point_down", "point_left", "point_right", "point_up", "point_up_2",
        "police_car", "poop", "post_office", "postbox", "pray", "princess",
        "punch", "purple_heart", "question", "rabbit", "racehorse", "radio",
        "up", "us", "v", "vhs", "vibration_mode", "virgo", "vs", "walking",
        "warning", "watermelon", "wave", "wc", "wedding", "whale", "wheelchair",
        "white_square", "wind_chime", "wink", "wink2", "wolf", "woman",
        "womans_hat", "womens", "x", "yellow_heart", "zap", "zzz", "+1",
        "-1"
    ]
    var jeremy = decodeURI("J%C3%A9r%C3%A9my") // Jérémy
    var names = ["Jacob","Isabella","Ethan","Emma","Michael","Olivia","Alexander","Sophia","William","Ava","Joshua","Emily","Daniel","Madison","Jayden","Abigail","Noah","Chloe","你好","你你你", jeremy, "가"];

    var names = $.map(names,function(value,i) {
        return {'id':i,'name':value,'email':value+"@email.com"};
    });
    var emojis = $.map(emojis, function(value, i) {return {key: value, name:value}});

    var at_config = {
        at: "*",
        data: "https://lagash.me/json/amigos",
        headerTpl: '<div class="atwho-header">Member List<small>↑&nbsp;↓&nbsp;</small></div>',
        insertTpl: '*${correo}',
        displayTpl: "<li>${correo} <small>${nombres}</small></li>",
        limit: 200
    }
    var emoji_config = {
        at: ":",
        data: emojis,
        displayTpl: "<li>${name} <img src='https://assets-cdn.github.com/images/icons/emoji/${key}.png'  height='20' width='20' /></li>",
        insertTpl: ':${key}:',
        delay: 400
    }
    $inputor = $('#inputor').atwho(at_config).atwho(emoji_config);
    $inputor.caret('pos', 47);
    $inputor.focus().atwho('run');

    emoji_config.insertTpl = "<img src='https://assets-cdn.github.com/images/icons/emoji/${name}.png'  height='20' width='20' />"
    $('#editable').atwho(at_config).atwho(emoji_config);

});