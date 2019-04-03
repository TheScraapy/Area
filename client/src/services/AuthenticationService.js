import Api from '@/services/Api'

export default {
  register (credentials) {
    return Api().post('auth/register', credentials);
  },
  login (credentials) {
    return Api().post('auth/login', credentials);
  },
  google (credentials) {
    return Api().post('auth/google', credentials);
  },
  oauth (id, credentials, token) {
    return Api().post('auth/' + id, credentials, { headers: {"Authorization" : `${token}`} });
  },
  twitterRequestTokens (credentials, token) {
    return Api().post('auth/twitter', credentials, { headers: {"Authorization" : `${token}`} });
  }
}
