import { useTranslation } from 'react-i18next';

import CircularProgress from '@mui/material/CircularProgress';

import StatelessWidget from '@/components/StatelessWidget';

const Loader = () => {
    const { t } = useTranslation();
    return (
        <StatelessWidget
            image={<CircularProgress />}
            title={`${t('loading')}...`}
        />
    );
};

export default Loader;
