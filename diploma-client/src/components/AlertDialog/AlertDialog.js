import React from 'react';
import PropTypes from 'prop-types';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogActions from '@material-ui/core/DialogActions';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  noTitlePadding: {
    paddingTop: theme.spacing(3),
  },
}));

const AlertDialog = ({
  dialogProps,
  title,
  contentText,
  dismissiveAction,
  confirmingAction,
  acknowledgementAction
}) => {
  const classes = useStyles();

  if ((dismissiveAction || confirmingAction) && acknowledgementAction) {
    console.error(
      'Dialogs should contain a maximum of two actions. ' +
      'If a single action is provided, it must be an acknowledgement action. ' +
      'If two actions are provided, one must be a confirming action, and the other a dismissing action. ' +
      'Providing a third action such as “Learn more” is not recommended as it navigates the user away from the dialog, leaving the dialog task unfinished. ' +
      'https://material.io/design/components/dialogs.html#actions'
    );
    return null;
  }

  return (
    <Dialog {...dialogProps}>
      {title && <DialogTitle>{title}</DialogTitle>}

      <DialogContent className={title ? null : classes.noTitlePadding}>
        <DialogContentText>{contentText}</DialogContentText>
      </DialogContent>

      {(dismissiveAction || confirmingAction || acknowledgementAction) && (
        <DialogActions>
          {dismissiveAction}
          {confirmingAction}
          {acknowledgementAction}
        </DialogActions>
      )}
    </Dialog>
  );
};

AlertDialog.propTypes = {
  dialogProps: PropTypes.object.isRequired,
  title: PropTypes.string,
  contentText: PropTypes.string.isRequired,
  dismissiveAction: PropTypes.element,
  confirmingAction: PropTypes.element,
  acknowledgementAction: PropTypes.element,
};

export default AlertDialog;