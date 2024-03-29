import { createContext, ReactNode, useContext, useMemo, useState } from 'react';

import { useMediaQuery } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';

type AppThemeControl = {
    toggleColorMode: () => void;
};

const AppThemeControlContext = createContext({} as AppThemeControl);

export function useAppThemeControl(): AppThemeControl {
    return useContext(AppThemeControlContext);
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
        [colorMode, darkThemePreferred]
    );
    const appThemeControl: AppThemeControl = {
        toggleColorMode: () => {
            setColorMode((prev) => determineColorMode(prev === 'light'));
        },
    };
    return (
        <AppThemeControlContext.Provider value={appThemeControl}>
            <ThemeProvider theme={theme}>
                {props.children}
            </ThemeProvider>
        </AppThemeControlContext.Provider>
    );
};

export default AppThemeProvider;
