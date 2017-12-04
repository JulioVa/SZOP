import os
import glob
import time
import json
import requests
import ConfigParser
import Adafruit_DHT
from requests.exceptions import ConnectionError

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')


def ConfigSectionMap(section):
    dict1 = {}
    options = config.options(section)
    for option in options:
        try:
            dict1[option] = config.get(section, option)
        except:
            print('exception on %s!' % option)
            dict1[option] = None
    return dict1


def read_ds18b20_raw(device_file):
    try:
        f = open(device_file, 'r')
        lines = f.readlines()
        f.close()
    except:
        print 'ds18b20 file not found'
        print 'probably disconnected sensor'
        lines = None
    return lines


def read_ds18b20(device_file):
    lines = read_ds18b20_raw(device_file)
    if lines is not None:
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


def save_ds18b20_value(sensor, file, date):
    data = {
        'id': sensor.split("/")[-1],
        'type': 'temp',
        'date': str(date),
        'value': read_ds18b20(sensor + '/w1_slave')
    }
    file.write('\n\t' + json.dumps(data) + '\n\t,')


def save_dht11_value(sensor_type, sensor, file, date):
    humid, temp = Adafruit_DHT.read_retry(sensor_type, sensor)
    if humid is not None and temp is not None:
        temp_data = {
            'id': str(sensor) + '/temp',
            'type': 'temp',
            'date': str(date),
            'value': float(temp)
        }
        file.write('\n\t' + json.dumps(temp_data) + '\n\t,')
        humid_data = {
            'id': str(sensor) + '/humid',
            'type': 'humid',
            'date': str(date),
            'value': float(humid)
        }
        file.write('\n\t' + json.dumps(humid_data) + '\n\t,')
    else:
        print 'Failed to read dht11 sensor for pin= ' + str(sensor)


def prepare_new_file(file):
    file.write('{\n')
    file.write(json.dumps('user_id') + ': ' + json.dumps(ConfigSectionMap('user data')['user_id']) + ',\n')
    file.write(json.dumps('system_id') + ': ' + json.dumps(ConfigSectionMap('user data')['system_id']) + ',\n')
    file.write(json.dumps('sensors') + ': [')


def prepare_existing_file(file):
    delete_last_line()
    file.write('\t,')

config = ConfigParser.ConfigParser()
config.read('config.ini')

url = ConfigSectionMap('user data')['url']
headers = {'Content-type': 'application/json'}

base_dir = '/sys/bus/w1/devices/'
ds18b20_list = glob.glob(base_dir + '28*')

sensor_type = Adafruit_DHT.DHT11
dht11_list = json.loads(config.get('dht11', 'sensors'))

while True:
    connected = ConfigSectionMap('connection')['connection']
    if connected == 'True':
        file = open('data.json', 'w+')
        prepare_new_file(file)
    else:
        file = open('data.json', 'a+')
        prepare_existing_file(file)
    date = long(time.time()*1000)
    for sensor in ds18b20_list:
        save_ds18b20_value(sensor, file, date)
    for sensor in dht11_list:
        save_dht11_value(sensor_type, sensor, file, date)

    delete_last_line()
    file.write('\n]}')
    file.close()
    with open('data.json', 'rb') as f:
        try:
            r = requests.post(url, data=f, headers=headers)
            if r.status_code != 200:
                config.set('connection', 'connection', 'False')
            else:
                config.set('connection', 'connection', 'True')
        except ConnectionError as e:
            print e
            r = 'No response'
            config.set('connection', 'connection', 'False')
        with open('config.ini', 'w') as cfg_file:
            config.write(cfg_file)
    time.sleep(300)
