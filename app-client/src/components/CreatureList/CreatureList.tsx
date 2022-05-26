import { useTranslation } from 'react-i18next';

import { DataGrid, GridColDef } from '@mui/x-data-grid';

import { Creature } from '@/generated/dnd/Creature';

export type Props = {
    items: Pick<Creature, 'name' | 'size' | 'type' | 'alignment'>[];
};

const CreatureList = ({ items }: Props) => {
    const { t } = useTranslation();

    const columns: GridColDef[] = [
        {
            flex: 0.25,
            field: 'name',
            headerName: t('Name'),
        },
        {
            flex: 0.25,
            field: 'size',
            headerName: t('Size'),
        },
        {
            flex: 0.25,
            field: 'type',
            headerName: t('Type'),
        },
        {
            flex: 0.25,
            field: 'alignment',
            headerName: t('Alignment'),
        },
    ];

    return (
        <div style={{ height: 400, width: '100%' }}>
            <DataGrid
                rows={items.map((item) => ({ id: item.name, ...item }))}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5]}
            />
        </div>
    );
};

export default CreatureList;
