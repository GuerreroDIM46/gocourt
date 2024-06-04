import axios from 'axios'

// const host = 'https://gocourt-5ef625746984.herokuapp.com/api/'
const host = 'https://gocourtapitest.manabo.org/api/'
const API_CAMPOS = host + 'campos'
const API_PARTIDOS = host + 'partidos'
const API_PARTIDOSHISTORICOS = host + 'partidos/search/partidosHistoricos'
const API_PARTIDOSVALIDADOS = host + 'partidos/search/partidosValidados'
const API_PARTIDOSPORCONFIRMAR = host + 'partidos/search/partidosPorConfirmar'
const API_FEDERADOS = host + 'federados'
const API_FEDERADOSSINPAGINAR = host + 'federados/search/federadosSinPaginacion'
const API_PRINCIPIANTES = host + 'principiantes'
const API_PRINCIPIANTESSINPAGINAR = host + 'principiantes/search/principiantesSinPaginacion'
const API_JUGADORESSIMILARES = host + 'jugadores/search/jugadoresNivelSimilar'
const API_FEDERADOSSIMILARES = host + 'jugadores/search/federadosNivelSimilar'
const API_PUNTUACIONES = host + 'puntuaciones'
const API_EMAIL = host + 'email'


function llamadaAPI(method, body, path) {
    let config = {
        method: method ?? "get",
        maxBodyLength: Infinity,
        url: path,
        headers: {}
    }
    if (body) {
        config.data = body
        config.headers["Content-Type"] = "application/json"
    }
    return axios.request(config)
}

export function getCampos() {
    return llamadaAPI('get', null, API_CAMPOS)
}

export function getPartidos() {
    return llamadaAPI('get', null, API_PARTIDOS)
}

export function getPartido(id) {
    const url = `${API_PARTIDOS}/${id}`
    return llamadaAPI('get', null, url)
}

export function getPartidosHistoricos() {
    return llamadaAPI('get', null, API_PARTIDOSHISTORICOS)
}

export function getPartidosValidados() {
    return llamadaAPI('get', null, API_PARTIDOSVALIDADOS)
}

export function getPartidosPorConfirmar() {
    return llamadaAPI('get', null, API_PARTIDOSPORCONFIRMAR)
}

export function getPuntuaciones() {
    return llamadaAPI('get', null, API_PUNTUACIONES)
}

export function getFederados() {
    return llamadaAPI('get', null, API_FEDERADOSSINPAGINAR)
}

export function getPrincipiantes() {
    return llamadaAPI('get', null, API_PRINCIPIANTESSINPAGINAR)
}

export function getJugadoresSimilares(id) {
    const url = `${API_JUGADORESSIMILARES}?id=${id}`
    console.log("lo que envia la funcion en APIservice es: ", url)
    return llamadaAPI('get', null, url)
}

export function getFederadosSimilares(id) {
    const url = `${API_FEDERADOSSIMILARES}?id=${id}`
    console.log("lo que envia la funcion en APIservice es: ", url)
    return llamadaAPI('get', null, url)
}

export function getPartidoPuntuaciones(id) {
    const url = `${API_PARTIDOS}/${id}/puntuaciones`
    return llamadaAPI('get', null, url)
}

export function postJugador(jugador) {
    const { tipo, ...jugadorSinTipo } = jugador
    const apiEndpoint = tipo == 'federado' ? API_FEDERADOS : API_PRINCIPIANTES
    return llamadaAPI('post', jugadorSinTipo, apiEndpoint)
}

export function postPartido(partido) {
    return llamadaAPI('post', partido, API_PARTIDOS)
}

export function putPartido(partido, id) {
    return llamadaAPI('patch', partido, id)
}

export function postAsignacion(asignacion) {
    return llamadaAPI('post', asignacion, API_PUNTUACIONES)
}

export function putJugador(jugador, id) {
    return llamadaAPI('patch', jugador, id)
}

export function deleteEntidad(href) {
    return llamadaAPI('delete', null, href)
}

export function sendSolicitudPartido(json) {
    const url = `${API_EMAIL}/sendComunicadoAsignacionDePartido`
    return llamadaAPI('post', json, url)
}
