import React, { MouseEventHandler } from 'react';

import Button from '@mui/material/Button';

import client from '@/api/client';
import { useObjectURL } from '@/utils/core-utils';
import { deserialize } from '@/utils/string-utils';

export type Props = {
    url: string;
    text: string;
};

const DownloadButton = ({ url, text }: Props) => {
    const download: MouseEventHandler = async () => {
        const { data, headers } = await client.get(url, { responseType: 'blob' });
        const contentDisposition = deserialize(headers['content-disposition']);
        const filename = contentDisposition['filename'] ?? 'charsheet.pdf';

        useObjectURL(data, (objectURL) => {
            const link = document.createElement('a');

            link.setAttribute('href', objectURL);
            link.setAttribute('download', filename);

            document.body.appendChild(link);

            link.click();
            link.remove();
        });
    };
    return (
        <Button onClick={download}>
            {text}
        </Button>
    );
};

export default DownloadButton;
