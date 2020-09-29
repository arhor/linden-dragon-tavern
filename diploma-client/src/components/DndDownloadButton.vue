<template>
    <v-btn @click.stop="download">Download charsheet</v-btn>
</template>

<script>
import axios from 'axios';
import { useObjectURL } from '@/utils/coreUtils.js';
import { deserialize } from '@/utils/stringUtils.js';

export default {
    name: 'DndDownloadButton',
    props: {
        url: {
            type: String,
            required: true,
        },
    },
    methods: {
        async download() {
            const { data, headers } = await axios.get(this.url, { responseType: 'blob' });
            const contentDisposition = deserialize(headers['content-disposition']);
            const filename = contentDisposition['filename'] ?? 'charsheet.pdf';

            useObjectURL(data, (url) => {
                const link = document.createElement('a');

                link.setAttribute('href', url);
                link.setAttribute('download', filename);

                document.body.appendChild(link);

                link.click();
                link.remove();
            });
        },
    },
};
</script>
