import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import NavBar from '@/components/NavBar';
import AppRouter from '@/components/AppRouter';

const AppLayout = () => {
  return (
    <BrowserRouter>
      <NavBar />
      <Container component='main'>
        <AppRouter />
      </Container>
    </BrowserRouter>
  );
};

export default AppLayout;