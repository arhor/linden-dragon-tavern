import React from 'react';
import { Link } from "react-router-dom";
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import Divider from '@material-ui/core/Divider';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { useSelector } from 'react-redux';
import { isAuthenticated, getRole } from '@/store/user';

const NavBar = () => {
  const authenticated = useSelector(isAuthenticated);
  const role = useSelector(getRole);

  const onSignUpClick = () => {
    // TODO: implement
  };

  const onSignInClick = () => {
    // TODO: implement
  };
      
  return (
    <AppBar color="primary" position="static">
      <Toolbar>
        <Box display="flex" flexGrow={1}>
          <Typography color="inherit" variant="h6">
            {process.env.REACT_APP_TITLE}
          </Typography>
        </Box>

        {authenticated && (
          <>
            {(role === 'admin') && (
              <Box mr={1}>
                <Button
                  color="inherit"
                  component={Link}
                  to="/admin"
                  variant="outlined"
                >
                  Admin
                </Button>
              </Box>
            )}

            <IconButton
              color="inherit"
              disabled={performingAction}
              onClick={this.openMenu}
            >
              <UserAvatar user={Object.assign(user, userData)} />
            </IconButton>

            <Menu
              anchorEl={menu.anchorEl}
              open={Boolean(menu.anchorEl)}
              onClose={this.closeMenu}
            >
              {menuItems.map((menuItem, index) => {
                if (menuItem.hasOwnProperty("condition") &&!menuItem.condition) {
                  return null;
                }

                let component = null;

                if (menuItem.to) {
                  component = (
                    <MenuItem
                      key={index}
                      component={Link}
                      to={menuItem.to}
                      onClick={this.closeMenu}
                    >
                      {menuItem.name}
                    </MenuItem>
                  );
                } else {
                  component = (
                    <MenuItem
                      key={index}
                      onClick={() => {
                        this.closeMenu();
                        menuItem.onClick();
                      }}
                    >
                      {menuItem.name}
                    </MenuItem>
                  );
                }

                if (menuItem.divide) {
                  return (
                    <span key={index}>
                      <Divider />

                      {component}
                    </span>
                  );
                }

                return component;
              })}
            </Menu>
          </>
        )}

        {!authenticated && (
          <ButtonGroup
            color="inherit"
            disabled={performingAction}
            variant="outlined"
          >
            <Button onClick={onSignUpClick}>Sign up</Button>
            <Button onClick={onSignInClick}>Sign in</Button>
          </ButtonGroup>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;