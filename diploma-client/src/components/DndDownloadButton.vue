<template functional>
    <v-btn @click.stop="$options.download(props.url)">Download charsheet</v-btn>
</template>

<script>
import axios from 'axios';

import { deserialize } from '@/utils/stringUtils.js';
import { useObjectURL } from '@/utils/coreUtils.js';

export default {
    name: 'DndDownloadButton',
    props: {
        url: {
            type: String,
            required: true,
        },
    },
    download: async (fileURL) => {
        const { data, headers } = await axios.get(fileURL, { responseType: 'blob' });
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
};
</script>
