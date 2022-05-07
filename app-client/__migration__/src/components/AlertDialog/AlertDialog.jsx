import React from 'react';

import log from 'loglevel';
import PropTypes from 'prop-types';

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

const ERROR_MSG_DIALOG = `\
Dialogs should contain a maximum of two actions. \
If a single action is provided, it must be an acknowledgement action. \
If two actions are provided, one must be a confirming action, and the other a dismissing action. \
Providing a third action such as �Learn more� is not recommended as it navigates the user away from the dialog, leaving the dialog task unfinished. \
https://material.io/design/components/dialogs.html#actions.
`;

process.env.NODE_ENV !== 'production' ? AlertDialog.propTypes = {
    dialogProps: PropTypes.object.isRequired,
    title: PropTypes.string,
    contentText: PropTypes.string.isRequired,
    dismissiveAction: PropTypes.element,
    confirmingAction: PropTypes.element,
    acknowledgementAction: PropTypes.element,
} : void 0;

function AlertDialog({
    dialogProps,
    title,
    contentText,
    dismissiveAction,
    confirmingAction,
    acknowledgementAction,
}) {
    if ((dismissiveAction || confirmingAction) && acknowledgementAction) {
        log.error(ERROR_MSG_DIALOG);
        return null;
    }

    return (
        <Dialog {...dialogProps} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
            {title && <DialogTitle id="alert-dialog-title">{title}</DialogTitle>}

            <DialogContent sx={title ? null : { paddingTop: (theme) => theme.spacing(3) }}>
                <DialogContentText id="alert-dialog-description">{contentText}</DialogContentText>
            </DialogContent>

            <DialogActions>
                {(dismissiveAction || confirmingAction || acknowledgementAction) && (
                    <DialogActions>
                        {dismissiveAction}
                        {confirmingAction}
                        {acknowledgementAction}
                    </DialogActions>
                )}
            </DialogActions>
        </Dialog>
    );
}

export default AlertDialog;
