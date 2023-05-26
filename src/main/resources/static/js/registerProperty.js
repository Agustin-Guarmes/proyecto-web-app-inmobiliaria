function gI(e){
    return document.getElementById(e);
}
const $selectProvincias = gI("selectProvincias");
const $selectLocalidades = gI("selectLocalidades");

function provincia() {
    fetch("https://apis.datos.gob.ar/georef/api/provincias?max=24")
    .then(res => res.ok ? res.json() : Promise.reject(res))
    .then(data => {

        let provinces = data.provincias;
            provinces.sort((prov1,prov2) => {
                let provinceA = prov1.nombre.toLowerCase();
                let provinceB = prov2.nombre.toLowerCase();
                if (provinceA < provinceB) {return -1;} else if (provinceA > provinceB) {return 1;} else {return 0;};
            });

        let $options = `<option hidden >Elige una provincia</option>`;

        provinces.forEach(e => $options += `<option value="${e.nombre}">${e.nombre}</option>`);

        $selectProvincias.innerHTML = $options;
    })
    .catch(error => {
        let message = error.statusText || "Ocurrió un error";

        $selectProvincias.nextElementSibling.innerHTML = `Error: ${error.status}: ${message}`;
    })
}

document.addEventListener("DOMContentLoaded", provincia)

function localidad(provincia) {
    fetch(`https://apis.datos.gob.ar/georef/api/localidades?provincia=${provincia}&max=500`)
    .then(res => res.ok ? res.json() : Promise.reject(res))
    .then(data => {

        let locations = data.localidades;
            locations.sort((loc1,loc2) => {
                let locationA = loc1.nombre.toLowerCase();
                let locationB = loc2.nombre.toLowerCase();
                if (locationA < locationB) {return -1;} else if (locationA > locationB) {return 1;} else {return 0;};
            });

        let $options;

        locations.forEach(e => $options += `<option value="${e.nombre}">${e.nombre}</option>`);

        $selectLocalidades.innerHTML = $options;
    })
    .catch(error => {
        let message = error.statusText || "Ocurrió un error";

        $selectLocalidades.nextElementSibling.innerHTML = `Error: ${error.status}: ${message}`;
    })
}

$selectProvincias.addEventListener("change", e => {
    localidad(e.target.value);
})

