import { defineStore } from 'pinia';
import { sendSolicitudPartido, sendPartidoAceptado } from '@/stores/APIservice.js';

export const useEmailAPIStore = defineStore('emailAPI', {
    actions: {
        async enviarEmailsSolicitud(urlPartido, urlAsignacion1, urlAsignacion2) {

            const apartidoId = extractIdFromUrl(urlPartido)
            const apuntuacion1Id = extractIdFromUrl(urlAsignacion1)
            const apuntuacion2Id = extractIdFromUrl(urlAsignacion2)

            const bodyCorreo1 = {
                partidoId: apartidoId,
                puntuacion1Id: apuntuacion1Id,
                puntuacion2Id: apuntuacion2Id
            }
            const bodyCorreo2 = {
                partidoId: apartidoId,
                puntuacion1Id: apuntuacion2Id,
                puntuacion2Id: apuntuacion1Id
            }
            console.log('el body que estoy enviando' + bodyCorreo1)
            const response1 = await sendSolicitudPartido(bodyCorreo1)
            const response2 = await sendSolicitudPartido(bodyCorreo2)

            console.log('Respuesta del correo 1:', response1)
            console.log('Respuesta del correo 2:', response2)
        },        
    },
    async enviarEmailsPartidoAceptado(urlPartido, urlAsignacion1, urlAsignacion2) {
        const apartidoId = extractIdFromUrl(urlPartido)
        const apuntuacion1Id = extractIdFromUrl(urlAsignacion1)
        const apuntuacion2Id = extractIdFromUrl(urlAsignacion2)
        const body = {
            partidoId: apartidoId,
            puntuacion1Id: apuntuacion1Id,
            puntuacion2Id: apuntuacion2Id
        }
        console.log('lo que envio',  body)
        const response = await sendPartidoAceptado(body)
        console.log('Respuesta del correo 1:', response)
    }
})

const extractIdFromUrl = (url) => {
    const parts = url.split('/')
    return parseInt(parts[parts.length - 1])
}