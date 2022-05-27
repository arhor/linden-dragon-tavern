import log from 'loglevel';

log.setLevel(process.env.NODE_ENV === 'development' ? 'DEBUG' : 'INFO');
