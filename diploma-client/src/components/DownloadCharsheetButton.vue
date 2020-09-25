<template>
    <v-btn @click.stop="download">Download charsheet</v-btn>
</template>

<script>
import axios from 'axios';
import { SERVER_API_URL } from '@/config/server-api';

const CHARACTER_SHEET_URL = `${SERVER_API_URL}/api/v1/charsheets`;

export default {
    name: 'DownloadCharsheetButton',
    methods: {
        async download() {
            const { data, headers } = await axios.get(CHARACTER_SHEET_URL, {
                responseType: 'blob',
            });

            const filename =
                headers['content-disposition']
                    ?.split(';')
                    ?.find((entry) => entry.startsWith('filename='))
                    ?.substring('filename='.length) ?? 'charsheet.pdf';

            const url = window.URL.createObjectURL(new Blob([data]));
            const link = document.createElement('a');

            link.href = url;
            link.setAttribute('download', filename);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        },
    },
};
</script>
