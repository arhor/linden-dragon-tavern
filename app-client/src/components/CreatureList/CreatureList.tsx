import { useTranslation } from 'react-i18next';

import { DataGrid, GridColDef } from '@mui/x-data-grid';

import { Creature } from '@/generated/dnd/Creature';

export type Props = {
    items: Partial<Creature>[];
};

const CreatureList = ({ items }: Props) => {
    const { t } = useTranslation();

    const columns: GridColDef[] = [
        {
            field: 'name',
            headerName: t('Name'),
        },
        {
            field: 'size',
            headerName: t('Size'),
        },
        {
            field: 'type',
            headerName: t('Type'),
        },
        {
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
                checkboxSelection
            />
        </div>
    );
};

export default CreatureList;
