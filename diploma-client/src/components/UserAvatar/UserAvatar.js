import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import PersonIcon from '@material-ui/icons/PersonIcon';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';

import authentication from '../../services/authentication';

const useStyles = makeStyles({
  nameInitials: {
    cursor: 'default',
  },
});

const UserAvatar = ({
  context = 'standalone',
  user,
  defaultCursor
}) => {
  const classes = useStyles();


  if (context === 'standalone') {
    if (!user) {
      return <AccountCircleIcon />;
    }

    const photoUrl = user.photoURL;

    if (photoUrl) {
      return <Avatar alt='Avatar' src={photoUrl} />;
    }

    const nameInitials = authentication.getNameInitials({
      ...user,
    });

    if (nameInitials) {
      return (
        <Avatar alt='Avatar'>
          <span className={defaultCursor && classes.nameInitials}>
            {nameInitials}
          </span>
        </Avatar>
      );
    }

    return <AccountCircleIcon />;
  }

  if (context === 'list') {
    if (!user) {
      return (
        <ListItemAvatar>
          <Avatar>
            <PersonIcon />
          </Avatar>
        </ListItemAvatar>
      );
    }

    const photoUrl = user.photoURL;

    if (photoUrl) {
      return (
        <ListItemAvatar>
          <Avatar alt='Avatar' src={photoUrl} />
        </ListItemAvatar>
      );
    }

    const nameInitials = authentication.getNameInitials({
      ...user,
    });

    if (nameInitials) {
      return (
        <ListItemAvatar>
          <Avatar alt='Avatar'>
            <span className={defaultCursor && classes.nameInitials}>
              {nameInitials}
            </span>
          </Avatar>
        </ListItemAvatar>
      );
    }

    return (
      <ListItemAvatar>
        <Avatar>
          <PersonIcon />
        </Avatar>
      </ListItemAvatar>
    );
  }

  return null;
};

UserAvatar.propTypes = {
  context: PropTypes.string,
  user: PropTypes.object.isRequired,
  defaultCursor: PropTypes.bool,
};

export default UserAvatar;