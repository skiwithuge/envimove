import json
import requests

#http://www.envibus.fr/flux.html?page=passages&dklik_boutique%5Baction%5D=autocomplete_arret&term=tem
def main():
    data = json.load(open('get_all_stations.json'))
    modified_list = []
    errors = 0
    name_errors = []
    for element in data['list']:
        name_arret = element['name'].replace(" ","%2B")
        url = "http://www.envibus.fr/flux.html?page=passages&dklik_boutique%5Baction%5D=autocomplete_arret&term="+name_arret
        try:
            r = requests.get(url)
            content = r.content.decode('utf8')
            dataUrl = json.loads(content)
            print(dataUrl[0]['id'])
            print(dataUrl[0])
            element['env_id'] = dataUrl[0]['id']
            modified_list.append(element)
        except:
            print("ERROR  "+name_arret)
            name_errors.append(name_arret)
            errors += 1

    print(data)

    with open('all_stations_cleaned.json', 'w') as outfile:
        json.dump(modified_list, outfile)

    print("Number of errors  " + str(errors))
    print("Names errors  " + str(name_errors))


if __name__ == '__main__':
    main()