import firebase_admin
from firebase_admin import credentials, firestore

cred = credentials.Certificate("hallowed-welder-297014-042131c68dd6.json")
firebase_admin.initialize_app(cred)

db = firestore.client()


def parse_interest(document_name):
    result = db.collection('totallySpies').document(document_name).get()

    if result.exists:
        parsed_dict = {k: v for k, v in result.to_dict().items() if v}
        return parsed_dict


def find_right_group(interest_group_c, interest_group_r, interest_group_p, interest_group_i, user_interests):
    groups_dict = {
        "group_I": set(interest_group_i),
        "group_P": set(interest_group_p),
        "group_R": set(interest_group_r),
        "group_C": set(interest_group_c)
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


def main():
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
    user_interestsB = {  # VARIABLE FOR TESTING PURPOSES
        "interest33": "1",
        "interest12": "1",
        "interest45": "1",
        "interest153": "1",
        "interest198": "1",
        "interest109": "1",
        "interest178": "1",
        "interest200": "1",
        "interest202": "1",
        "interest217": "1",
    }
    interest_group_c = parse_interest('C')
    interest_group_i = parse_interest('I')
    interest_group_p = parse_interest('P')
    interest_group_r = parse_interest('R')
    find_right_group(interest_group_c, interest_group_r, interest_group_p, interest_group_i, user_interestsB)


if __name__ == "__main__":
    main()
