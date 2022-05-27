import { useTranslation } from 'react-i18next';

import CircularProgress from '@mui/material/CircularProgress';

import logo from '@/assets/svg/logo.svg';
import StatelessWidget from '@/components/StatelessWidget';

const Loader = () => {
    const { t } = useTranslation();
    return (
        <StatelessWidget
            image={
                <>
                    <img src={logo} alt={`${t('Loading')}...`} />
                    <br />
                    <CircularProgress />
                </>
            }
        />
    );
};

export default Loader;
