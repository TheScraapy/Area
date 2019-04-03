import Api from '@/services/Api'

export default {
  getWidgets (token) {
    return Api().get('widgets/all', { headers: {"Authorization" : `${token}`} })
  },
  getServices (token) {
    return Api().get('widgets/services', { headers: {"Authorization" : `${token}`} })
  },
  getActions (route, token) {
    return Api().get('widgets/' + route + '/actions', { headers: {"Authorization" : `${token}`} })
  },
  getReactions (route, token) {
    return Api().get('widgets/' + route + '/reactions', { headers: {"Authorization" : `${token}`} })
  },
  createWidget (body, token) {
    return Api().post('widgets/add', body, { headers: {"Authorization" : `${token}`} })
  },
  toggleWidget (body, token) {
    return Api().post('widgets/toggle', body, { headers: {"Authorization" : `${token}`} })
  },
  deleteWidget (body, token) {
    return Api().post('widgets/delete', body, { headers: {"Authorization" : `${token}`} })
  }
}
