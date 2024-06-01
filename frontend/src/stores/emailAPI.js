import { defineStore } from 'pinia';
import { sendSolicitudPartido } from '@/stores/APIservice.js';

export const useEmailAPIStore = defineStore('emailAPI', {
    actions: {
        async enviarEmailsSolicitud(urlPartido, urlAsignacion1, urlAsignacion2) {

            const partidoId = extractIdFromUrl(urlPartido)
            const puntuacion1Id = extractIdFromUrl(urlAsignacion1)
            const puntuacion2Id = extractIdFromUrl(urlAsignacion2)

    const bodyCorreo1 = {
                partidoId: partidoId,
                puntuacion1Id: puntuacion1Id,
                puntuacion2Id: puntuacion2Id
            }
            const bodyCorreo2 = {
                partidoId: partidoId,
                puntuacion1Id: puntuacion2Id,
                puntuacion2Id: puntuacion1Id
            }

            const response1 = await sendSolicitudPartido(bodyCorreo1)
            const response2 = await sendSolicitudPartido(bodyCorreo2)

            console.log('Respuesta del correo 1:', response1)
            console.log('Respuesta del correo 2:', response2)
        }
    }
})

const extractIdFromUrl = (url) => {
    const parts = url.split('/')
    return parseInt(parts[parts.length - 1])
}