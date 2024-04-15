import axios from 'axios'

const host = 'https://https://gocourt-5ef625746984.herokuapp.com/api/'
const API_CAMPOS = host + 'campos'
const API_FEDERADOS = host + 'federados'
const API_PRINCIPIANTES = host + 'principiantes'

async function llamadaAPI(method, body, path) {
    let config = {
    method: method ?? "get",
    maxBodyLength: Infinity,
    url: path,
    headers: {}
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

export function getFederados() {
  return llamadaAPI('get', null, API_FEDERADOS)
}

export function getPrincipiantes() {
  return llamadaAPI('get', null, API_PRINCIPIANTES)
}
