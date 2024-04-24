import axios from 'axios';

const host = 'https://gocourt-5ef625746984.herokuapp.com/api/';
const API_CAMPOS = host + 'campos';
const API_FEDERADOS = host + 'federados';
const API_FEDERADOSSINPAGINAR = host + 'federados/search/federadosSinPaginacion';
const API_PRINCIPIANTES = host + 'principiantes';
const API_PRINCIPIANTESSINPAGINAR = host + 'principiantes/search/principiantesSinPaginacion';
const API_JUGADORESSIMILARES = host + 'jugadores/search/jugadoresNivelSimilar'
const API_FEDERADOSSIMILARES = host + 'jugadores/search/federadosNivelSimilar'


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

export function getJugadoresSimilares(id) {
    const url = `${API_JUGADORESSIMILARES}?id=${id}`;
    console.log("lo que envia la funcion en APIservice es: ", url)
    return llamadaAPI('get', null, url);
}

export function getFederadosSimilares(id) {
    const url = `${API_FEDERADOSSIMILARES}?id=${id}`;
    console.log("lo que envia la funcion en APIservice es: ", url)
    return llamadaAPI('get', null, url);
}

export function postJugador(jugador) {
    const { tipo, ...jugadorSinTipo } = jugador;
    const apiEndpoint = tipo === 'federado' ? API_FEDERADOS : API_PRINCIPIANTES;
    return llamadaAPI('post', jugadorSinTipo, apiEndpoint);
}

export function putJugador(jugador, id) {
    return llamadaAPI('patch', jugador, id);
}

export function deleteJugador(id) {
    return llamadaAPI('delete', null, id);
}
