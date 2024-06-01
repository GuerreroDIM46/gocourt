import { defineStore } from 'pinia';
import { sendSolicitudPartido } from '@/stores/APIservice.js';

export const useEmailAPIStore = defineStore('emailAPI', {
    actions: {
        async enviarEmailsSolicitud(urlPartido, urlAsignacion1, urlAsignacion2) {
            const bodyCorreo1 = {
                partidoUrl: urlPartido,
                puntuacion1Url: urlAsignacion1,
                puntuacion2Url: urlAsignacion2
            };
            const bodyCorreo2 = {
                partidoUrl: urlPartido,
                puntuacion1Url: urlAsignacion2,
                puntuacion2Url: urlAsignacion1
            };

            const response1 = await sendSolicitudPartido(bodyCorreo1);
            const response2 = await sendSolicitudPartido(bodyCorreo2);

            console.log('Respuesta del correo 1:', response1);
            console.log('Respuesta del correo 2:', response2);
        }
    }
});