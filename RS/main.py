from flask import Flask, request, jsonify
import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

app = Flask(__name__)


@app.route('/')
def index():
     return "TotallySpies is so Fun hihi"


@app.route('/users', methods=['POST'])
def add_user():
    right_group = []

    user = {
        'first_name': request.json['first_name'],
        'last_name': request.json['last_name'],
        'interests': request.json['interests']
    }

    user_interests_dict = {interest: "1" for interest in user['interests']}
    right_group = find_right_group(user_interests_dict)

    data = {
        u'first name': user['first_name'],
        u'last name': user['last_name'],
        u'interests': user['interests']
    }
    # Add a new doc in collection 'cities' with ID 'LA'
    for i in right_group:
        db.collection(u'totallySpies').document(i).collection('users').add(data)

    return jsonify(right_group)


def find_right_group(user_interests):
    interest_group_c = parse_interest('C')
    interest_group_i = parse_interest('I')
    interest_group_p = parse_interest('P')
    interest_group_r = parse_interest('R')
    groups_dict = {
        "I": set(interest_group_i),
        "P": set(interest_group_p),
        "R": set(interest_group_r),
        "C": set(interest_group_c)
    }
    set_user = set(user_interests)
    right_group = {
        "group": [],
        "value": []
    }

    for key, value in groups_dict.items():
        if not right_group['group']:
            right_group['group'].append(key)
            right_group['value'].append(value.intersection(set_user))
        elif len(right_group['value'][0]) < len(value.intersection(set_user)):
            right_group['group'].clear()
            right_group['value'].clear()
            right_group['group'].append(key)
            right_group['value'].append(value.intersection(set_user))
        elif len(right_group['value'][0]) == len(value.intersection(set_user)):
            right_group['group'].append(key)
            right_group['value'].append(value.intersection(set_user))

    return right_group['group']


def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        return parsed_dict


if __name__ == '__main__':
    app.run(debug=True, port=8080)