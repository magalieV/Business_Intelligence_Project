import random
import names

import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db = firestore.client()


def add_users(nb_users):
    for i in range(nb_users):
        interests = []
        for j in range(10):
            interests.append("interest" + str(random.randint(1, 217)))
        user = {
            u'first name': names.get_first_name(),
            u'last name': names.get_last_name(),
            u'interests': interests
        }

        user_interests_dict = {interest: "1" for interest in user['interests']}
        right_group = find_right_group(user_interests_dict)

        db.collection(u'totallySpies').document(right_group).collection('users').add(user)

def get_user(document_name):
    all_groups = ["C", "I", "P", "R"]
    all_users_in_group = None

    for i in all_groups:
        doc_ref = db.collection(u'totallySpies').document(i).collection('users').document(document_name)
        doc = doc_ref.get()
        if doc.exists:
            print(doc.to_dict())
            all_users_in_group = db.collection('totallySpies').document(i).collection('users').stream()
            break
    all_users = []
    for users in all_users_in_group:
        all_users.append({k: v for k, v in users.to_dict().items() if v})
    return all_users

def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        return parsed_dict


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

        print("Group : {}, Common interests : {}".format(key, value.intersection(set_user)))
    print("\n// Match Groups : {}\n// Common interests : {}".format(right_group['group'], right_group['value']))
    return right_group['group']


def main():
    # add_users(2)
    user_interests = ["interest33", "interest12", "interest45", "interest153", "interest198", "interest109", "interest178",
                     "interest200", "interest202", "interest217"]

    user_interest_form = {interest: "1" for interest in user_interests}
    user_interestsA = {  # VARIABLE FOR TESTING PURPOSES
        "interest1": "1",
        "interest207": "1",
        "interest134": "1",
        "interest59": "1",
        "interest64": "1",
        "interest137": "1",
        "interest23": "1",
        "interest9": "1",
        "interest14": "1",
        "interest89": "1",
    }
    # user_interestsB = {  # VARIABLE FOR TESTING PURPOSES
    #     "interest33": "1",
    #     "interest12": "1",
    #     "interest45": "1",
    #     "interest153": "1",
    #     "interest198": "1",
    #     "interest109": "1",
    #     "interest178": "1",
    #     "interest200": "1",
    #     "interest202": "1",
    #     "interest217": "1",
    # }

    #find_right_group(user_interest_form)
    print(get_user("eT1plVcx7CO1PND23gQB"))

if __name__ == "__main__":
    main()
