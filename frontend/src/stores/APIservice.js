import axios from 'axios'

const host = 'https://gocourt-5ef625746984.herokuapp.com/api/'
const API_CAMPOS = host + 'campos'
const API_FEDERADOS = host + 'federados'
const API_PRINCIPIANTES = host + 'principiantes'

async function llamadaAPI(method, body, path, params) {
    let config = {
        method: method ?? "get",
        maxBodyLength: Infinity,
        url: path,
        headers: {},
        params: params  
    }
    if (body) {
        config.data = body;
        config.headers["Content-Type"] = "application/json"
    }
    return axios.request(config);
}

export function getCampos() {
    return llamadaAPI('get', null, API_CAMPOS)
}

export function getFederados(pagina = 0, tamanoPagina = 20) {
    const params = {
        page: pagina,
        size: tamanoPagina
    };
    return llamadaAPI('get', null, API_FEDERADOS, params);
}

export function getPrincipiantes(pagina = 0, tamanoPagina = 20) {
    const params = {
        page: pagina,
        size: tamanoPagina
    };
    return llamadaAPI('get', null, API_PRINCIPIANTES, params);
}
