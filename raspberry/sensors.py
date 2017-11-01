import os
import glob
import time
import datetime
import json
import requests


os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')


def read_ds18b20_raw(device_file):
    f = open(device_file, 'r')
    lines = f.readlines()
    f.close()
    return lines


def read_ds18b20(device_file):
    lines = read_ds18b20_raw(device_file)
    while lines[0].strip()[-3:] != 'YES':
        time.sleep(0.2)
        lines = read_ds18b20_raw(device_file)
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = float(temp_string) / 1000.0
        return temp_c


def delete_last_line():
    file.seek(0, os.SEEK_END)
    pos = file.tell() - 1
    while pos > 0 and file.read(1) != '\n':
        pos -= 1
        file.seek(pos, os.SEEK_SET)
    if pos > 0:
        file.seek(pos, os.SEEK_SET)
        file.truncate()


def save_ds18b20_value(sensor, file):
    data = {
        'id': sensor.split("/")[-1],
        'type': 'temp',
        'date': datetime.datetime.now().strftime("%Y-%m-%d %H:%M"),
        'value': read_ds18b20(sensor + '/w1_slave')
    }
    file.write('\n\t' + json.dumps(data) + '\n\t,')


connected = True
base_dir = '/sys/bus/w1/devices/'
ds18b20_list = glob.glob(base_dir + '28*')
#device_file = device_folder + '/w1_slave'

#todo populate DHT11

while True:
    if connected is True:
        file = open('data.json', 'w+')
        file.write('{' + json.dumps('sensors') + ': [')
    else:
        file = open('data.json', 'a+')
        delete_last_line()
        file.write('\t,')
    for sensor in ds18b20_list:
        save_ds18b20_value(sensor, file)

    #todo save DHT11 data

    delete_last_line()
    file.write('\n]}')
    file.close()
    with open('data.json', 'rb') as f:
        r = requests.post(url, files={'data.json': f})
        if r.status_code != 201:
            connected = False
        else:
            connected = True
    time.sleep(300)