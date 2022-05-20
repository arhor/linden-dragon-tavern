import { createContext, ReactNode, useContext, useMemo, useState } from 'react';

import { useMediaQuery } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';

type ColorMode = {
    toggleColorMode: () => void;
};

const ColorModeContext = createContext<ColorMode>({ toggleColorMode: () => void 0 });

const AppThemeProvider = (props: { children: ReactNode }) => {
    const shouldUseDarkTheme = useMediaQuery('(prefers-color-scheme: dark)');
    const [mode, setMode] = useState<'light' | 'dark'>(shouldUseDarkTheme ? 'light' : 'light');

    const colorMode = useMemo(
        () => ({
            toggleColorMode: () => {
                setMode((prevMode) => (prevMode === 'light' ? 'dark' : 'light'));
            },
        }),
        []
    );
    const theme = useMemo(
        () => createTheme({
            palette: {
                mode,
            },
        }),
        [mode]
    );

    return (
        <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
                {props.children}
            </ThemeProvider>
        </ColorModeContext.Provider>
    );
};

export default AppThemeProvider;

export const useColorMode = () => useContext(ColorModeContext);
