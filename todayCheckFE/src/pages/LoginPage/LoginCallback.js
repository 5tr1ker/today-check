import { useLocation, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { setAccessToken, setRefreshToken } from '../../cookie/Cookie';
import axios from 'axios';

const LoginCallback = () => {
  const [token, setToken] = useState(null);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (location.hash) {
      const accessToken = location.hash.split('=')[1].split('&')[0];
      axios
        .get(
          'https://www.googleapis.com/oauth2/v2/userinfo?access_token=' +
            accessToken,
          {
            headers: {
              authorization: `Bearer ${accessToken}`,
            },
          }
        )
        .then(data => {
          console.log(data.config.headers.authorization);
          setToken(data.config.headers.authorization);
          axios
            .post(`/login?code=${token}`)
            .then(response => {
              setAccessToken(response.data.accessToken);
              setRefreshToken(response.data.accessToken);
              navigate('/');
            })
            .catch(err => {
              alert('로그인 실패!');
            });
        })
        .catch(e => console.log('oAuth token expired'));
    }
  }, []);
};

export default LoginCallback;
