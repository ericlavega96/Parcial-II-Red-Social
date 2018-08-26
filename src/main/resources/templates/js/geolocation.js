document.addEventListener('DOMContentLoaded', init);

function init(){
    if(navigator.geolocation){
        options ={
            enableHighAccuracy: true,
            timeout: 3000,      //30 segundos
            maximumAge: 36000   //1 hora
        }

        navigator.geolocation.getCurrentPosition(gotPos, posFail, options);
    }else{
        //using an old browser that doesn't support geolocation
    }
}

function gotPos(position){
    //geolocalizacion.textContent = "(" + position.coords.latitude + ", "  + position.coords.longitude + ")";
    document.getElementById("geolocalizacion").value = "Ubicación Actual: (" + position.coords.latitude + ", "  + position.coords.longitude + ")";
}

function posFail(err){
    //err is a number
    errors = {
        1: 'No permission',
        2: 'Unable to determine',
        3: 'Took too long'
    }
    document.querySelector('h1').textContent = errors[err];
}
