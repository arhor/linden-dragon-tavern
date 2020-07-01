import React from 'react';
import { useSelector } from 'react-redux';
import {getAvatarUrl, getFirstName, getLastName } from '@/store/account';

const Account = () => {
  const avatarUrl = useSelector(getAvatarUrl);
  const firstName = useSelector(getFirstName);
  const lastName = useSelector(getLastName);

  return (
    <div>
      <img src={avatarUrl} alt="account avatar"/>

      <label>First Name: </label>
      <input>{firstName}</input>

      <label>Last Name: </label>
      <input>{lastName}</input>
    </div>
  );
};

export default Account;
