// import { Link } from 'react-router-dom';
// import AppBar from '@mui/material/AppBar';
// import Toolbar from '@material-ui/core/Toolbar';
// import Typography from '@material-ui/core/Typography';
// import Box from '@material-ui/core/Box';
// import ButtonGroup from '@material-ui/core/ButtonGroup';
// import Button from '@material-ui/core/Button';
// import IconButton from '@material-ui/core/IconButton';
// import Divider from '@material-ui/core/Divider';
// import Menu from '@material-ui/core/Menu';
// import MenuItem from '@material-ui/core/MenuItem';
// import { useSelector } from 'react-redux';
// import { isAuthenticated } from '@/store/user';
// import { onSignInClick, onSignUpClick } from '@/components/AppNavBar/handlers';

// const eligibleItem = (item) => !item.hasOwnProperty('condition') || item.condition;

// const AppNavBar = ({ menuItems }) => {
//     const authenticated = true; //useSelector(isAuthenticated);

//     return (
//         <AppBar color="primary" position="static">
//             <Toolbar>
//                 <Box display="flex" flexGrow={1}>
//                     <Typography color="inherit" variant="h6">
//                         {process.env.REACT_APP_TITLE}
//                     </Typography>
//                 </Box>

//                 <Box mr={1}>
//                     <Button color="inherit" component={Link} to="/" variant="outlined">
//                         Home
//                     </Button>
//                 </Box>

//                 <Box mr={1}>
//                     <Button color="inherit" component={Link} to="/about" variant="outlined">
//                         About
//                     </Button>
//                 </Box>

//                 {
//                     // authenticated && (
//                     //   <>
//                     //     {(role === 'admin') && (
//                     //       <Box mr={1}>
//                     //         <Button
//                     //           color="inherit"
//                     //           component={Link}
//                     //           to="/admin"
//                     //           variant="outlined"
//                     //         >
//                     //           Admin
//                     //         </Button>
//                     //       </Box>
//                     //     )}
//                     //     <IconButton
//                     //       color="inherit"
//                     //       // disabled={performingAction}
//                     //       onClick={this.openMenu}
//                     //     >
//                     //       {/* <UserAvatar user={Object.assign(user, userData)} /> */}
//                     //     </IconButton>
//                     //     <Menu
//                     //       // anchorEl={menu.anchorEl}
//                     //       // open={Boolean(menu.anchorEl)}
//                     //       onClose={this.closeMenu}
//                     //     >
//                     //       {menuItems.filter(eligibleItem).map((menuItem, index) => {
//                     //         let component = null;
//                     //         if (menuItem.to) {
//                     //           component = (
//                     //             <MenuItem
//                     //               key={index}
//                     //               component={Link}
//                     //               to={menuItem.to}
//                     //               onClick={this.closeMenu}
//                     //             >
//                     //               {menuItem.name}
//                     //             </MenuItem>
//                     //           );
//                     //         } else {
//                     //           component = (
//                     //             <MenuItem
//                     //               key={index}
//                     //               onClick={() => {
//                     //                 this.closeMenu();
//                     //                 menuItem.onClick();
//                     //               }}
//                     //             >
//                     //               {menuItem.name}
//                     //             </MenuItem>
//                     //           );
//                     //         }
//                     //         if (menuItem.divide) {
//                     //           return (
//                     //             <span key={index}>
//                     //               <Divider />
//                     //               {component}
//                     //             </span>
//                     //           );
//                     //         }
//                     //         return component;
//                     //       })}
//                     //     </Menu>
//                     //   </>
//                     // )
//                 }

//                 {!authenticated && (
//                     <ButtonGroup
//                         color="inherit"
//                         // disabled={performingAction}
//                         variant="outlined"
//                     >
//                         <Button onClick={onSignUpClick}>Sign up</Button>
//                         <Button onClick={onSignInClick}>Sign in</Button>
//                     </ButtonGroup>
//                 )}
//             </Toolbar>
//         </AppBar>
//     );
// };

// export default AppNavBar;

import MenuIcon from '@mui/icons-material/Menu';
import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

const AppNavBar = () => (
    <AppBar position="static">
        <Toolbar>
            <IconButton size="large" edge="start" color="inherit" aria-label="menu" sx={{ mr: 2 }}>
                <MenuIcon />
            </IconButton>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                Linden Dragon Tavern
            </Typography>
            <Button color="inherit">Login</Button>
        </Toolbar>
    </AppBar>
);

export default AppNavBar;