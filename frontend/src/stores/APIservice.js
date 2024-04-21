import axios from 'axios';

const host = 'https://gocourt-5ef625746984.herokuapp.com/api/';
const API_CAMPOS = host + 'campos';
const API_FEDERADOS = host + 'federados';
const API_FEDERADOSSINPAGINAR = host + 'federados/search/federadosSinPaginacion';
const API_PRINCIPIANTES = host + 'principiantes';
const API_PRINCIPIANTESSINPAGINAR = host + 'principiantes/search/principiantesSinPaginacion';

function llamadaAPI(method, body, path) {
    let config = {
        method: method ?? "get",
        maxBodyLength: Infinity,
        url: path,
        headers: {}
    };
    if (body) {
        config.data = body;
        config.headers["Content-Type"] = "application/json";
    }
    return axios.request(config);
}

export function getCampos() {
    return llamadaAPI('get', null, API_CAMPOS);
}

export function getFederados() {
    return llamadaAPI('get', null, API_FEDERADOSSINPAGINAR);
}

export function getPrincipiantes() {
    return llamadaAPI('get', null, API_PRINCIPIANTESSINPAGINAR);
}

export function postJugador(jugador) {
    const { tipo, ...jugadorSinTipo } = jugador;
    const apiEndpoint = tipo === 'federado' ? API_FEDERADOS : API_PRINCIPIANTES;
    return llamadaAPI('post', jugadorSinTipo, apiEndpoint);
}

export function putJugador(jugador, id) {
    return llamadaAPI('put', jugador, id);
}

export function deleteJugador(id) {
    return llamadaAPI('delete', null, id);
}
