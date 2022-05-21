import { createContext, ReactNode, useContext, useMemo, useState } from 'react';

import { useMediaQuery } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';

export type AppThemeControl = {
    toggleColorMode: () => void;
};

const AppThemeContext = createContext({} as AppThemeControl);

export function useAppTheme(): AppThemeControl {
    return useContext(AppThemeContext);
}

function determineColorMode(shouldUseDarkTheme: boolean) {
    return shouldUseDarkTheme ? 'dark' : 'light';
}

const AppThemeProvider = (props: { children: ReactNode }) => {
    const [colorMode, setColorMode] = useState<'light' | 'dark'>();
    const darkThemePreferred = useMediaQuery('(prefers-color-scheme: dark)');
    const theme = useMemo(
        () => createTheme({
            palette: {
                mode: colorMode ?? determineColorMode(darkThemePreferred),
            },
        }),
        [darkThemePreferred, colorMode]
    );
    const appThemeControl: AppThemeControl = {
        toggleColorMode: () => {
            setColorMode((prev) => determineColorMode(prev === 'light'));
        },
    };
    return (
        <AppThemeContext.Provider value={appThemeControl}>
            <ThemeProvider theme={theme}>
                {props.children}
            </ThemeProvider>
        </AppThemeContext.Provider>
    );
};

export default AppThemeProvider;
